import { Component, OnInit } from '@angular/core';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';
import { icon, latLng, marker, polyline, tileLayer } from 'leaflet';
import * as L from 'leaflet';
import { NgbTooltipConfig } from '@ng-bootstrap/ng-bootstrap';
import { LoginModalService, Principal, Account } from 'app/core';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';
import { CourseService } from 'app/entities/course';
import { ICourse } from 'app/shared/model/course.model';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.scss'],
    providers: [NgbTooltipConfig]
})
export class HomeComponent implements OnInit {
    account: Account;
    modalRef: NgbModalRef;
    page: any;
    reverse: any;

    model: any;
    searchFailed = false;

    events: ICourse[];
    eventsNames: string[];

    filters = {
        COURSE_A_PIED: false,
        MARATHON: false,
        RANDONNEE: false,
        COURSE_A_VELO: false,
        TRIATHLON: false
    };

    /* Define our base layers so we can reference them multiple times */
    OSMaps = tileLayer('http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '© <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>',
        maxZoom: 20,
        detectRetina: true
    });

    /* Marker center of France */
    center = marker([46.4547, 8.2529], {
        icon: icon({
            iconSize: [25, 41],
            iconAnchor: [13, 41],
            iconUrl: require('../../../../../node_modules/leaflet/dist/images/marker-icon.png'),
            shadowUrl: require('../../../../../node_modules/leaflet/dist/images/marker-shadow.png')
        })
    });

    // Set the initial set
    options = {
        layers: [this.OSMaps, this.center],
        zoom: 4,
        center: latLng([46.4547, 2.2529])
    };

    constructor(
        private principal: Principal,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private config: NgbTooltipConfig,
        private jhiAlertService: JhiAlertService,
        private courseService: CourseService
    ) {
        config.placement = 'right';
    }

    ngOnInit() {
        this.principal.identity().then(account => {
            this.account = account;
        });
        this.registerAuthenticationSuccess();
        this.events = [];
        const tableau = [];
        const tmp = this;

        const promise = new Promise((resolve, reject) => {
            this.courseService
                .getCourses()
                .toPromise()
                .then(res => {
                    // Success
                    this.events = res.body;

                    const eventsNames: string[] = [];
                    this.events.forEach(function(value: ICourse) {
                        eventsNames.push(value.titre);
                    });
                    this.eventsNames = eventsNames;
                })
                .then(res => {
                    tableau.push([46.4547, 6.2529]);
                    tableau.push([46.4547, 3.2529]);
                    tableau.push([46.4547, 4.2529]);
                    tmp.options.layers.push(
                        marker([46.4547, 2.2529], {
                            icon: icon({
                                iconSize: [25, 41],
                                iconAnchor: [13, 41],
                                iconUrl: require('../../../../../node_modules/leaflet/dist/images/marker-icon.png'),
                                shadowUrl: require('../../../../../node_modules/leaflet/dist/images/marker-shadow.png')
                            })
                        })
                    );
                    resolve();
                });
        });

        Promise.all([promise]).then(res => {
            tmp.options.layers.push(
                marker([46.4547, 2.2529], {
                    icon: icon({
                        iconSize: [25, 41],
                        iconAnchor: [13, 41],
                        iconUrl: require('../../../../../node_modules/leaflet/dist/images/marker-icon.png'),
                        shadowUrl: require('../../../../../node_modules/leaflet/dist/images/marker-shadow.png')
                    })
                })
            );

            this.options = {
                layers: [this.OSMaps],
                zoom: 4,
                center: latLng([46.4547, 2.2529])
            };
            const mape = L.map('leaflet-map').setView([46.4547, 2.2529], 5);
            this.OSMaps.addTo(mape);

            this.events.forEach(function(value: ICourse) {
                marker([value.latitude, value.longitude], {
                    icon: icon({
                        iconSize: [25, 41],
                        iconAnchor: [13, 41],
                        iconUrl: require('../../../../../node_modules/leaflet/dist/images/marker-icon.png'),
                        shadowUrl: require('../../../../../node_modules/leaflet/dist/images/marker-shadow.png')
                    })
                }).addTo(mape);
            });
        });
    }

    registerAuthenticationSuccess() {
        this.eventManager.subscribe('authenticationSuccess', message => {
            this.principal.identity().then(account => {
                this.account = account;
            });
        });
    }

    isAuthenticated() {
        return this.principal.isAuthenticated();
    }

    login() {
        this.modalRef = this.loginModalService.open();
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    search = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(term => (term === '' ? [] : this.eventsNames.filter(v => v.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10)))
        );
}
