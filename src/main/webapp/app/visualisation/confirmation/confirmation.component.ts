import { Component, OnInit } from '@angular/core';
import { UserService, Principal, IUser } from 'app/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { ICourse } from 'app/shared/model/course.model';
import { ICheckPoint } from 'app/shared/model/check-point.model';
import { Observable } from 'rxjs/internal/Observable';

@Component({
    selector: 'jhi-confirmation',
    templateUrl: './confirmation.component.html',
    styles: []
})
export class ConfirmationComponent implements OnInit {
    public text: String = ' ';
    public currentUser: IUser;
    public course: ICourse;
    public courseDB: ICourse;
    private isSaving: Boolean;
    private cp: ICheckPoint;

    constructor(
        private principal: Principal,
        private userService: UserService,
        private http: HttpClient,
        private activatedRoute: ActivatedRoute
    ) {}

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICheckPoint>>) {
        result.subscribe((res: HttpResponse<ICheckPoint>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

    getUser(data) {
        this.userService.find(data.login).subscribe((user: HttpResponse<IUser>) => {
            if (user.body) {
                this.currentUser = user.body;
                this.text = '{ "user": "' + JSON.stringify(this.currentUser.id) + '", "course": "' + JSON.stringify(this.course.id) + '"}';
            } else {
                console.log('Error retreiving user.body');
            }
        });
    }

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ course }) => {
            this.course = course;
        });
        this.principal.identity().then(data => this.getUser(data));
    }
}
