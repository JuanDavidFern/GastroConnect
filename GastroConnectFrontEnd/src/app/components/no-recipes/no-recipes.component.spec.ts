import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoRecipesComponent } from './no-recipes.component';

describe('NoRecipesComponent', () => {
  let component: NoRecipesComponent;
  let fixture: ComponentFixture<NoRecipesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NoRecipesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NoRecipesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
