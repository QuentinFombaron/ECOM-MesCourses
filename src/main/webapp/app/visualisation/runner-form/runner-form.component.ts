import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserService, Principal, IUser } from 'app/core';
import { ICourse } from 'app/shared/model/course.model';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from 'app/entities/course';
import { Observable } from 'rxjs/internal/Observable';
import { HttpResponse } from '@angular/common/http';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
    selector: 'jhi-runner-form',
    templateUrl: './runner-form.component.html',
    styleUrls: ['runner-form.scss']
})
export class RunnerFormComponent implements OnInit {
    currentUser: IUser;
    course: ICourse;
    courseDB: ICourse;
    isSaving: boolean;

    constructor(
        private courseService: CourseService,
        private userService: UserService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router
    ) {}

    fillForm(data: any) {
        this.userService.find(data.login).subscribe((user: HttpResponse<IUser>) => {
            if (user.body) {
                this.currentUser = user.body;
            } else {
                console.log('Error retreiving user.body');
            }
        });
        if (this.principal.isAuthenticated()) {
            (<HTMLInputElement>document.getElementById('InputNameR')).value = data.lastName;
            (<HTMLInputElement>document.getElementById('InputSurnameR')).value = data.firstName;
            (<HTMLInputElement>document.getElementById('InputEmailR')).value = data.email;
        }
    }

    ngOnInit() {
        this.principal.identity().then(data => this.fillForm(data));
        this.activatedRoute.data.subscribe(({ course }) => {
            this.course = course;
        });
        this.courseService.find(this.course.id).subscribe(data => (this.courseDB = data.body));
    }

    backBasePage() {
        document.getElementById('JoinDiv').style.display = 'block';
        document.getElementById('joinRunnersDiv').style.display = 'none';
        document.getElementById('joinBenevolesDiv').style.display = 'none';
    }

    onSubmit() {
        this.router.navigate(['/confirmation', this.course.id]);
    }

    save() {
        this.isSaving = true;
        if (this.course.id !== undefined) {
            this.subscribeToSaveResponse(this.courseService.update(this.course));
        } else {
            this.subscribeToSaveResponse(this.courseService.create(this.course));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ICourse>>) {
        result.subscribe((res: HttpResponse<ICourse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
    }

    private onSaveError() {
        this.isSaving = false;
    }

    submittedRunnerForm() {
        /* TODO : Décommenter */
        /*for (let i = 0; i < this.course.participants.length; i++) {
            if (this.course.participants[i].email === this.currentUser.email) {
                alert('Vous êtes déjà inscrit à cette course');
                return;
            }
        }*/
        this.course.participants.push(this.currentUser);
        this.courseDB.participants.push(this.currentUser);
        this.save();
        document.getElementById('greyWrapper').style.display = 'none';
        document.getElementById('greyWrapper2').style.display = 'block';
    }
}
