import { httpGet, httpPost } from './http';

export const downloadTaskFiles = (taskId: string) =>
  httpGet<Blob>(`/file/task/${taskId}/downloadAll`, {
    responseType: 'blob'
  });

export const uploadTaskFile = (taskId: string, file: File) => {
  const formData = new FormData();
  formData.append('taskId', taskId);
  formData.append('file', file);
  return httpPost<boolean>('/taskAssign/completeTask', formData, {
    headers: { 'Content-Type': 'multipart/form-data' }
  });
};


