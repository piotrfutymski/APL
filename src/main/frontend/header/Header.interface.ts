import {FunctionComponent} from "react";

export interface Subpage{
    link: string
    label: string;
}

export interface HeaderProps{
    subpages: Subpage[];
    showLogo: boolean;
}