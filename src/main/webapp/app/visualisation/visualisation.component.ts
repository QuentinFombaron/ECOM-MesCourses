import { Component, OnInit } from '@angular/core';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { LoginModalService, Principal, UserService, IUser } from 'app/core';
import { ICourse } from 'app/shared/model/course.model';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { isUndefined } from 'util';
import { CourseService } from 'app/entities/course';

@Component({
    selector: 'jhi-visualisation',
    templateUrl: './visualisation.component.html',
    styleUrls: ['visualisation.scss']
})
export class VisualisationComponent implements OnInit {
    course: ICourse;
    organisateur: IUser;
    pictures: String[] = [];

    events: ICourse[] = [];
    organisateurs: IUser[] = [];

    constructor(
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private loginModalService: LoginModalService,
        private eventManager: JhiEventManager,
        private userService: UserService,
        private router: Router,
        private courseService: CourseService
    ) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ course }) => {
            this.course = course;
            for (let i = 1; i < 6; i++) {
                if (course['image' + i] !== null) {
                    this.pictures.push(course['image' + i]);
                }
            }
        });

        this.courseService.query().subscribe(
            (res: HttpResponse<ICourse[]>) => {
                this.events = res.body;
                this.getOrganisateurs();
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    joinRunnersClicked() {
        if (this.principal.isAuthenticated()) {
            document.getElementById('JoinDiv').style.display = 'none';
            document.getElementById('joinRunnersDiv').style.display = 'block';
        } else {
            this.router.navigate(['/register']);
        }
    }

    joinBenevolesClicked() {
        if (this.principal.isAuthenticated()) {
            document.getElementById('JoinDiv').style.display = 'none';
            document.getElementById('joinBenevolesDiv').style.display = 'block';
        } else {
            this.router.navigate(['/register']);
        }
    }

    openImg(imgs: string) {
        /* Get the expanded image */
        const expandImg = document.getElementById('expandedImg');
        /* Use the same src in the expanded image as the image being clicked on from the grid */
        (<HTMLImageElement>expandImg).src = 'data:image/png;base64,' + imgs;
        /* Show the container element (hidden with CSS) */
        expandImg.parentElement.style.display = 'block';
    }

    getPictures() {
        return this.pictures;
    }

    getCoureursInscrits() {
        return this.course.participants;
    }

    getOrganisateurs() {
        for (const event of this.events) {
            this.userService.findById(event.organisateur).subscribe(
                (res: HttpResponse<IUser>) => {
                    this.organisateurs.push(res.body);
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        }
    }

    getOrganisateurById(id) {
        const user = this.organisateurs.find(function(value) {
            return value.id === id;
        });
        if (!isUndefined(user)) {
            return user.firstName + ' ' + user.lastName;
        } else {
            return null;
        }
    }

    getSportString(sport) {
        switch (sport) {
            case 'COURSE_A_PIED':
                return 'Course à pied';
            case 'MARATHON':
                return 'Marathon';
            case 'RANDONNEE':
                return 'Randonnée';
            case 'COURSE_A_VELO':
                return 'Course à vélo';
            case 'TRIATHLON':
                return 'Triathlon';
        }
    }
}
