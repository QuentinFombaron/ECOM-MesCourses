import { element, by, ElementFinder } from 'protractor';

export class InscriptionComponentsPage {
    createButton = element(by.id('jh-create-entity'));
    deleteButtons = element.all(by.css('jhi-inscription div table .btn-danger'));
    title = element.all(by.css('jhi-inscription div h2#page-heading span')).first();

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

export class InscriptionUpdatePage {
    pageTitle = element(by.id('jhi-inscription-heading'));
    saveButton = element(by.id('save-entity'));
    cancelButton = element(by.id('cancel-save'));
    roleSelect = element(by.id('field_role'));
    userInput = element(by.id('field_user'));
    courseInput = element(by.id('field_course'));

    async getPageTitle() {
        return this.pageTitle.getText();
    }

    async setRoleSelect(role) {
        await this.roleSelect.sendKeys(role);
    }

    async getRoleSelect() {
        return this.roleSelect.element(by.css('option:checked')).getText();
    }

    async roleSelectLastOption() {
        await this.roleSelect
            .all(by.tagName('option'))
            .last()
            .click();
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

export class InscriptionDeleteDialog {
    private dialogTitle = element(by.id('jhi-delete-inscription-heading'));
    private confirmButton = element(by.id('jhi-confirm-delete-inscription'));

    async getDialogTitle() {
        return this.dialogTitle.getText();
    }

    async clickOnConfirmButton() {
        await this.confirmButton.click();
    }
}
