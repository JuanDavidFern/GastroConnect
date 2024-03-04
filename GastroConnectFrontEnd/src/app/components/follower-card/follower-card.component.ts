import { Component, Input } from '@angular/core';
import { FollowCard } from '../../interfaces/FollowCard';
import { NavigationService } from '../../services/navigation.service';

@Component({
  selector: 'app-follower-card',
  templateUrl: './follower-card.component.html',
  styleUrls: ['./follower-card.component.css'],
})
export class FollowerCardComponent {
  @Input() follower!: FollowCard;

  constructor(private navigationService: NavigationService) {}

  ngOnInit() {
    this.follower.registration_date = this.formatTimestamp(
      parseInt(this.follower.registration_date)
    );
  }

  goProfile(userId: any) {
    this.navigationService.goProfile(userId);
  }

  private formatTimestamp(timestamp: number): string {
    const date = new Date(timestamp);
    // Utilizar toLocaleDateString() en lugar de toLocaleString()
    return date.toLocaleDateString();
  }
}
