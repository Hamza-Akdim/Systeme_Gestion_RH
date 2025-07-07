import {Component, ElementRef, inject, ViewChild} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule} from '@angular/router';
import {AppMenuitem} from './app.menuitem';

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
            label: 'Tableau de bord',
            icon: 'pi pi-home',
            items: [
                {
                    label: 'Accueil',
                    icon: 'pi pi-fw pi-home',
                    routerLink: ['/']
                }
            ]
        },
        {
            label: 'Administration',
            icon: 'pi pi-cog',
            items: [
                {
                    label: 'Gestion des utilisateurs',
                    icon: 'pi pi-fw pi-users',
                    routerLink: ['/admin/users']
                },
                {
                    label: 'Gestion des rôles',
                    icon: 'pi pi-fw pi-id-card',
                    routerLink: ['/admin/roles']
                },
                {
                    label: 'Paramètres système',
                    icon: 'pi pi-fw pi-sliders-h',
                    routerLink: ['/admin/settings']
                }
            ]
        },
        { separator: true },
        {
            label: 'Authentification',
            icon: 'pi pi-fw pi-user',
            items: [
                {
                    label: 'Connexion',
                    icon: 'pi pi-fw pi-sign-in',
                    routerLink: ['/auth/login']
                },
                {
                    label: 'Inscription',
                    icon: 'pi pi-fw pi-user-plus',
                    routerLink: ['/auth/register']
                },
                {
                    label: 'Mot de passe oublié',
                    icon: 'pi pi-fw pi-question',
                    routerLink: ['/auth/forgotpassword']
                },
                {
                    label: 'Profil utilisateur',
                    icon: 'pi pi-fw pi-users',
                    routerLink: ['/auth/profile']
                },
                {
                    label: 'Réinitialisation du MDP',
                    icon: 'pi pi-fw pi-cog',
                    routerLink: ['/auth/resetpassword']
                }
            ]
        },
        { separator: true },
        {
            label: 'Candidats',
            icon: 'pi pi-user',
            items: [
                {
                    label: 'Liste des candidats',
                    icon: 'pi pi-list',
                    routerLink: ['/pages/candidate'] // Au lieu de '/candidate/candidate-list'
                },
                {
                    label: 'Candidatures',
                    icon: 'pi pi-file',
                    routerLink: ['/pages/candidate/applications'] // Au lieu de '/candidate'
                }
            ]
        },
        { separator: true },
        {
            label: 'Business',
            icon: 'pi pi-fw pi-briefcase',
            items: [
                {
                    label: 'Prospects',
                    icon: 'pi pi-fw pi-users',
                    routerLink: ['/pages/business']
                },
                {
                    label: 'Enquêtes',
                    icon: 'pi pi-fw pi-chart-bar',
                    routerLink: ['/pages/business/surveys']
                }
            ]
        },


        {
            label: 'Évaluation',
            icon: 'pi pi-star',
            items: [
                {
                    label: 'Revues annuelles',
                    icon: 'pi pi-calendar-plus',
                    routerLink: ['/evaluation/annual-reviews']
                },
                {
                    label: 'Évaluations de compétences',
                    icon: 'pi pi-check-square',
                    routerLink: ['/evaluation/skill-assessments']
                }
            ]
        },
        {
            label: 'Ressources Humaines',
            icon: 'pi pi-briefcase',
            items: [
                {
                    label: 'Profils de poste',
                    icon: 'pi pi-id-card',
                    routerLink: ['/hr/job-profiles']
                },
                {
                    label: "Plans d'exécution",
                    icon: 'pi pi-calendar',
                    routerLink: ['/hr/execution-plans']
                }
            ]
        },
        {
            label: 'Missions',
            icon: 'pi pi-briefcase',
            items: [
                {
                    label: 'Liste des missions',
                    icon: 'pi pi-list',
                    routerLink: ['/mission/list']
                },
                {
                    label: 'Nouvelle mission',
                    icon: 'pi pi-plus',
                    routerLink: ['/mission/new']
                }
            ]
        },
        {
            label: 'Intégration',
            icon: 'pi pi-sign-in',
            items: [
                {
                    label: "Processus d'intégration",
                    icon: 'pi pi-list',
                    routerLink: ['/onboarding/list']
                },
                {
                    label: 'Nouveau processus',
                    icon: 'pi pi-plus',
                    routerLink: ['/onboarding/new']
                }
            ]
        },
        {
            label: 'Départ',
            icon: 'pi pi-sign-out',
            items: [
                {
                    label: 'Processus de départ',
                    icon: 'pi pi-list',
                    routerLink: ['/outboarding/list']
                },
                {
                    label: 'Nouveau processus',
                    icon: 'pi pi-plus',
                    routerLink: ['/outboarding/new']
                }
            ]
        },
        {
            label: 'Sélection',
            icon: 'pi pi-filter',
            items: [
                {
                    label: 'Processus de sélection',
                    icon: 'pi pi-list',
                    routerLink: ['/selection/list']
                },
                {
                    label: 'Nouveau processus',
                    icon: 'pi pi-plus',
                    routerLink: ['/selection/new']
                }
            ]
        },
        {
            label: 'Sourcing',
            icon: 'pi pi-search',
            items: [
                {
                    label: 'Campagnes de sourcing',
                    icon: 'pi pi-list',
                    routerLink: ['/sourcing/list']
                },
                {
                    label: 'Nouvelle campagne',
                    icon: 'pi pi-plus',
                    routerLink: ['/sourcing/new']
                }
            ]
        },
        {
            label: 'Feuilles de temps',
            icon: 'pi pi-clock',
            items: [
                {
                    label: 'Mes feuilles de temps',
                    icon: 'pi pi-calendar',
                    routerLink: ['/timesheet/list']
                },
                {
                    label: 'Nouvelle feuille',
                    icon: 'pi pi-plus',
                    routerLink: ['/timesheet/new']
                }
            ]
        }
    ]
}
