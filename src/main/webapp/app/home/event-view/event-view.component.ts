import { Component, OnInit } from '@angular/core';
import { NgbTooltipConfig } from '@ng-bootstrap/ng-bootstrap';
import { CourseService } from 'app/entities/course';
import { ICourse } from 'app/shared/model/course.model';
import { HttpResponse } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';
import { IUser, UserService } from 'app/core';
import { isUndefined } from 'util';

@Component({
    selector: 'jhi-event-view',
    templateUrl: './event-view.component.html',
    styles: [],
    providers: [NgbTooltipConfig]
})
export class EventViewComponent implements OnInit {
    activeItem = 1;

    events: ICourse[] = [];
    organisateurs: IUser[] = [];

    constructor(private jhiAlertService: JhiAlertService, private courseService: CourseService, private userService: UserService) {}

    ngOnInit() {
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
