import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { IUser, Principal, UserService } from 'app/core';

@Component({
    selector: 'jhi-benevoles-form',
    templateUrl: './benevoles-form.component.html',
    styles: []
})
export class BenevolesFormComponent implements OnInit {
    currentUser: IUser;

    constructor(private userService: UserService, private principal: Principal) {}

    ngOnInit() {
        this.principal.identity().then(data => this.fillForm(data));
    }

    backBasePage() {
        document.getElementById('JoinDiv').style.display = 'block';
        document.getElementById('joinRunnersDiv').style.display = 'none';
        document.getElementById('joinBenevolesDiv').style.display = 'none';
    }

    fillForm(data: any) {
        this.userService.find(data.login).subscribe((user: HttpResponse<IUser>) => {
            if (user.body) {
                this.currentUser = user.body;
            } else {
                console.log('Error retreiving user.body');
            }
        });
        if (this.principal.isAuthenticated()) {
            (<HTMLInputElement>document.getElementById('InputNameB')).value = data.lastName;
            (<HTMLInputElement>document.getElementById('InputSurnameB')).value = data.firstName;
            (<HTMLInputElement>document.getElementById('InputEmailB')).value = data.email;
        }
    }
}
