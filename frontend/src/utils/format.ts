import dayjs from 'dayjs';

export const formatDateTime = (val?: string | Date) =>
  val ? dayjs(val).format('YYYY-MM-DD HH:mm:ss') : '-';


