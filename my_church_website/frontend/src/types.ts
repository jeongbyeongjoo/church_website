// 설교 데이터 타입
export interface SermonDto {
  id: string;
  title: string;
  speaker: string;
  date: string;
  series: string;
  passage: string;
}

// 행사 데이터 타입
export interface EventItemDto {
  id: string;
  name: string;
  date: string;
  time: string;
  location: string;
  description: string;
}

// 사역 데이터 타입
export interface MinistryDto {
  id:string;
  name: string;
  leader: string;
  description: string;
  imageUrl: string;
}