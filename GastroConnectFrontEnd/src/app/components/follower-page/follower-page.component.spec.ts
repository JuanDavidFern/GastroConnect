import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FollowerPageComponent } from './follower-page.component';

describe('FollowerPageComponent', () => {
  let component: FollowerPageComponent;
  let fixture: ComponentFixture<FollowerPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FollowerPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FollowerPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
