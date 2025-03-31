import React, { useState } from "react";
import axios from "axios";
import ApiButtons from "../components/ApiButtons";
import ApiForm from "../components/ApiForm";

const ApiPage: React.FC = () => {
  const [loading, setLoading] = useState(false);
  type ApiResponse = {
    status: number | string | undefined; // 상태 코드 (숫자 또는 문자열)
    data: string | null; // 응답 데이터
  };

  const [apiResponse, setApiResponse] = useState<ApiResponse | null>(null);

  const handleApiRequest = async (
    url: string,
    method: string,
    columnName?: string,
    inputValue?: string
  ) => {
    try {
      const fullUrl = `http://localhost:8080/api/v1/${url}`; // API 베이스 URL 설정
      let response;

      const requestData = columnName && inputValue ? { [columnName]: inputValue } : {};

      console.log(requestData);

      if (method === "GET") {
        response = await axios.get(fullUrl);
      } else if (method === "POST") {
        response = await axios.post(fullUrl, requestData); // 필요하면 데이터 추가
      } else if (method === "DELETE") {
        const productId = requestData.id;
        const deleteUrl = `http://localhost:8080/api/v1/brand/deleteProduct/${productId}`; // DELETE URL에 productId 포함

        response = await axios.delete(deleteUrl);
      } else if (method === "PUT") {
        response = await axios.put(fullUrl, { updateData: "updatedValue" });
      }

      //   const data = await response;
      //   setApiResponse(JSON.stringify(data, null, 2));
      const statusCode = response?.status;
      const data = response?.data;

      // 상태 업데이트
      setApiResponse({
        status: statusCode,
        data: JSON.stringify(data, null, 2),
      });
    } catch (error) {
      if (axios.isAxiosError(error)) {
        // 서버에서 반환한 에러 메시지 처리
        const errorData = error.response?.data; // { "code": 400, "message": "바지없음 is not found in categories" }
        setApiResponse({
          status: `Error : ${errorData.code}`,
          data: `Message: ${errorData.message}`,
        });
      } else {
        setApiResponse({
          status: "Error IN React AXIOS",
          data: `Error: ${error}`,
        });
      }
    }
  };

  const submitProductData = async (formValues: { [key: string]: string }) => {
    const formData = new FormData();

    Object.entries(formValues).forEach(([key, value]) => {
      if (value && value.trim() !== "") {
        formData.append(key, value);
      }
    });
    const productId = formValues.id;
    try {
      const response = await axios.patch(
        `http://localhost:8080/api/v1/brand/editProduct/${productId}`,
        formData,
        {
          headers: {
            "Content-Type": "application/json",
          },
        }
      );

      setApiResponse({
        status: response.status,
        data: JSON.stringify(response.data, null, 2),
      });
    } catch (error) {
      if (axios.isAxiosError(error)) {
        // 서버에서 반환한 에러 메시지 처리
        const errorData = error.response?.data; // { "code": 400, "message": "바지없음 is not found in categories" }
        setApiResponse({
          status: `Error : ${errorData.code}`,
          data: `Message: ${errorData.message}`,
        });
      } else {
        setApiResponse({
          status: "Error IN React AXIOS",
          data: `Error: ${error}`,
        });
      }
    }
  };

  return (
    <div className="api-tester-container">
      <h2>API Tester</h2>
      <ApiButtons onFetchData={handleApiRequest} loading={loading} />
      <ApiForm onSubmit={submitProductData} />
      {apiResponse && (
        <div className="response-container">
          <h3>Response Status:</h3>
          <pre className="response-box">{apiResponse.status}</pre>
          <h3>Response data:</h3>
          <pre className="response-box">{apiResponse.data}</pre>
        </div>
      )}
    </div>
  );
};

export default ApiPage;
