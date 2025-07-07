export interface Prospect {
    id?: string;
    firstName?: string;
    lastName?: string;
    company?: string;
    position?: string;
    email?: string;
    phone?: string;
    address?: string;
    city?: string;
    country?: string;
    postalCode?: string;
    status?: string;
    source?: string;
    assignedTo?: string;
    notes?: string;
    rating?: number;
    lastContact?: Date;
    nextContact?: Date;
    createdAt?: Date;
    updatedAt?: Date;
    tags?: string[];
    budget?: number;
    opportunity?: number;
}

export interface ProspectStatus {
    label: string;
    value: string;
}

export interface ProspectSource {
    label: string;
    value: string;
}
