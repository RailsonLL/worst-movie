export class PageResult<T> {
  page!: number;
  totalElements!: number;
  content: T[] = [];
}
