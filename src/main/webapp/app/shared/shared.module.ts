import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { JavatrainingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [JavatrainingSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [JavatrainingSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JavatrainingSharedModule {
  static forRoot() {
    return {
      ngModule: JavatrainingSharedModule
    };
  }
}
