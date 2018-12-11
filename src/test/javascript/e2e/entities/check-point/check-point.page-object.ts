import { element, by, ElementFinder } from 'protractor';

export class CheckPointComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-check-point div table .btn-danger'));
    title = element.all(by.css('jhi-check-point div h2#page-heading span')).first();

    async clickOnCreateButton() {
        await this.createButton.click();
    }

    async clickOnLastDeleteButton() {
        await this.deleteButtons.last().click();
    }

    async countDeleteButtons() {
        return this.deleteButtons.count();
    }

    async getTitle() {
        return this.title.getText();
    }
}

export class CheckPointUpdatePage {
    pageTitle = element(by.id('jhi-check-point-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    heureInput = element(by.id('field_heure'));
    userInput = element(by.id('field_user'));
    courseInput = element(by.id('field_course'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setHeureInput(heure) {
        await this.heureInput.sendKeys(heure);
    }

    async getHeureInput() {
        return this.heureInput.getAttribute('value');
    }

    async setUserInput(user) {
        await this.userInput.sendKeys(user);
    }

    async getUserInput() {
        return this.userInput.getAttribute('value');
    }

    async setCourseInput(course) {
        await this.courseInput.sendKeys(course);
    }

    async getCourseInput() {
        return this.courseInput.getAttribute('value');
    }

    async save() {
        await this.saveButton.click();
    }

    async cancel() {
        await this.cancelButton.click();
    }

    getSaveButton(): ElementFinder {
        return this.saveButton;
    }
}

export class CheckPointDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-checkPoint-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-checkPoint'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
