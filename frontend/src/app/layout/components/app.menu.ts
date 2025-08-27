import { Component, ElementRef, inject, ViewChild } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AppMenuitem } from './app.menuitem';

interface MenuItem {
    label?: string;
    icon?: string;
    routerLink?: string[];
    url?: string[];
    target?: '_blank' | '_self' | '_parent' | '_top';
    routerLinkActiveOptions?: { [key: string]: any };
    items?: MenuItem[];
    separator?: boolean;
    visible?: boolean;
    disabled?: boolean;
    command?: (event?: any) => void;
    class?: string;
    style?: string;
    styleClass?: string;
    id?: string;
    urlTarget?: '_blank' | '_self' | '_parent' | '_top';
    roles?: string[];
}

@Component({
    selector: '[app-menu]',
    standalone: true,
    imports: [CommonModule, AppMenuitem, RouterModule],
    template: `<ul class="layout-menu" #menuContainer>
        <ng-container *ngFor="let item of model; let i = index">
            <li app-menuitem *ngIf="!item.separator" [item]="item" [index]="i" [root]="true"></li>
            <li *ngIf="item.separator" class="menu-separator"></li>
        </ng-container>
    </ul>`,
    host: {
        class: 'layout-menu-container'
    }
})
export class AppMenu {
    el: ElementRef = inject(ElementRef);

    @ViewChild('menuContainer') menuContainer!: ElementRef;

    model: MenuItem[] = [
        {
            label: 'Offers',
            icon: 'pi pi-home',
            roles: ['CONSULTANT'],
            items: [{ label: 'Home', icon: 'pi pi-fw pi-home', routerLink: ['/'] }]
        },
        {
            label: 'Offers',
            icon: 'pi pi-list',
            roles: ['ADMIN', 'HR'], // all roles
            items: [
                { label: 'Offer Management', icon: 'pi pi-fw pi-list', routerLink: ['/hr/job-offer'] },
                { label: 'New Offer', icon: 'pi pi-fw pi-plus', routerLink: ['/hr/job-offer/createOffer'] }
            ]
        },

        {
            label: 'Administration',
            icon: 'pi pi-cog',
            roles: ['ADMIN'],
            items: [
                { label: 'User Management', icon: 'pi pi-fw pi-users', routerLink: ['/admin/users'] },
                { label: 'Role Management', icon: 'pi pi-fw pi-id-card', routerLink: ['/admin/roles'] },
                { label: 'System Settings', icon: 'pi pi-fw pi-sliders-h', routerLink: ['/admin/settings'] }
            ]
        },
        { separator: true },
        {
            label: 'Authentication',
            icon: 'pi pi-fw pi-user',
            roles: ['ADMIN', 'HR', 'CONSULTANT'],
            items: [
                { label: 'Login', icon: 'pi pi-fw pi-sign-in', routerLink: ['/auth/login'] },
                { label: 'Register', icon: 'pi pi-fw pi-user-plus', routerLink: ['/auth/register'] },
                { label: 'Forgot Password', icon: 'pi pi-fw pi-question', routerLink: ['/auth/forgotpassword'] },
                { label: 'User Profile', icon: 'pi pi-fw pi-users', routerLink: ['/auth/profile'] },
                { label: 'Password Reset', icon: 'pi pi-fw pi-cog', routerLink: ['/auth/resetpassword'] }
            ]
        },
        { separator: true },
        {
            label: 'Candidates',
            icon: 'pi pi-user',
            items: [{ label: 'Candidate List', icon: 'pi pi-list', routerLink: ['/pages/candidate'], roles: ['ADMIN', 'HR'] }]
        },
        { separator: true },
        {
            label: 'My Applications',
            icon: 'pi pi-user',
            items: [{ label: 'Applications', icon: 'pi pi-file', routerLink: ['/pages/candidate/applications'], roles: ['CONSULTANT'] }]
        },
        { separator: true },

        {
            label: 'Human Resources',
            icon: 'pi pi-briefcase',
            roles: ['ADMIN', 'HR'],
            items: [
                // { label: 'Job Offer', icon: 'pi pi-id-card', routerLink: ['/hr/job-offer'] },
                { label: 'Job Profiles', icon: 'pi pi-id-card', routerLink: ['/hr/job-profiles'] },
                { label: 'Execution Plans', icon: 'pi pi-calendar', routerLink: ['/hr/execution-plans'] }
            ]
        },
        {
            label: 'Business',
            icon: 'pi pi-fw pi-briefcase',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'Prospects', icon: 'pi pi-fw pi-users', routerLink: ['/pages/business'] },
                { label: 'Surveys', icon: 'pi pi-fw pi-chart-bar', routerLink: ['/pages/business/surveys'] }
            ]
        },
        {
            label: 'Evaluation',
            icon: 'pi pi-star',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'Annual Reviews', icon: 'pi pi-calendar-plus', routerLink: ['/evaluation/annual-reviews'] },
                { label: 'Skill Assessments', icon: 'pi pi-check-square', routerLink: ['/evaluation/skill-assessments'] }
            ]
        },

        {
            label: 'Missions',
            icon: 'pi pi-briefcase',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'Mission List', icon: 'pi pi-list', routerLink: ['/mission/list'] },
                { label: 'New Mission', icon: 'pi pi-plus', routerLink: ['/mission/new'] }
            ]
        },
        {
            label: 'Onboarding',
            icon: 'pi pi-sign-in',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'Onboarding Processes', icon: 'pi pi-list', routerLink: ['/onboarding/list'] },
                { label: 'New Process', icon: 'pi pi-plus', routerLink: ['/onboarding/new'] }
            ]
        },
        {
            label: 'Offboarding',
            icon: 'pi pi-sign-out',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'Offboarding Processes', icon: 'pi pi-list', routerLink: ['/outboarding/list'] },
                { label: 'New Process', icon: 'pi pi-plus', routerLink: ['/outboarding/new'] }
            ]
        },
        {
            label: 'Selection',
            icon: 'pi pi-filter',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'Selection Processes', icon: 'pi pi-list', routerLink: ['/selection/list'] },
                { label: 'New Process', icon: 'pi pi-plus', routerLink: ['/selection/new'] }
            ]
        },
        {
            label: 'Sourcing',
            icon: 'pi pi-search',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'Sourcing Campaigns', icon: 'pi pi-list', routerLink: ['/sourcing/list'] },
                { label: 'New Campaign', icon: 'pi pi-plus', routerLink: ['/sourcing/new'] }
            ]
        },
        {
            label: 'Timesheets',
            icon: 'pi pi-clock',
            roles: ['ADMIN', 'HR'],
            items: [
                { label: 'My Timesheets', icon: 'pi pi-calendar', routerLink: ['/timesheet/list'] },
                { label: 'New Timesheet', icon: 'pi pi-plus', routerLink: ['/timesheet/new'] }
            ]
        }
    ];

    ngOnInit() {
        const roles = (localStorage.getItem('roles') || '').split(',').map((r) => r.trim());
        this.model = this.filterMenuByRoles(this.model, roles);
    }

    private filterMenuByRoles(items: MenuItem[], userRoles: string[]): MenuItem[] {
        return items
            .map((item) => {
                // Check if the item has role restrictions
                if (item.roles && !item.roles.some((r) => userRoles.includes(r))) {
                    return null; // Hide item
                }

                // Check children recursively
                if (item.items) {
                    item.items = this.filterMenuByRoles(item.items, userRoles);
                    // If no visible children, hide parent
                    if (item.items.length === 0 && !item.routerLink) {
                        return null;
                    }
                }

                return item;
            })
            .filter(Boolean) as MenuItem[];
    }
}
