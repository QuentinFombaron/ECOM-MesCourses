import { NgModule } from '@angular/core';

import { MesCoursesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [MesCoursesSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [MesCoursesSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class MesCoursesSharedCommonModule {}
