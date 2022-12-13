import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'convertWinner', pure: false })
export class ConvertWinner implements PipeTransform {
    constructor() {}
    transform(value: boolean) {
        if (value == null) {
            return null;
        }

        return value == true ? 'Yes' : 'No';
    }
}
