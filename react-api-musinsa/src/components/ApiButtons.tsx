// src/components/ApiButtons.tsx
import axios from "axios";
import React, { useState } from "react";

type ApiButtonsProps = {
  onFetchData: (apiType: string, method: string, columnName?: string, input?: string) => void;
  loading: boolean;
};

const ApiButtons: React.FC<ApiButtonsProps> = ({ onFetchData, loading }) => {
  let idx = 0;
  //   const [inputValues, setInputValues] = useState<string[]>([]);
  const [inputValues, setInputValues] = useState<Record<string, string>>({});

  const apiEndpoints = [
    { url: "product/health", method: "GET" },
    { url: "product/getMinPriceByCategories", method: "GET" },
    { url: "product/getMinPriceByBrand", method: "GET" },
    { url: "product/getBothPriceByCategory", method: "POST", columnName: "category" },
    { url: "brand/create", method: "POST", columnName: "brand" },
    { url: "brand/deleteProduct", method: "DELETE", columnName: "id" },
  ];

  const handleInputChange = (columnName: string, value: string) => {
    setInputValues((prevValues) => ({
      ...prevValues,
      [columnName]: value, // columnName을 키로 사용하여 각 값 업데이트
    }));
  };

  return (
    <div className="button-container">
      {apiEndpoints.map((api) =>
        ["product/getBothPriceByCategory", "brand/create", "brand/deleteProduct"].includes(
          api.url
        ) ? (
          <div key={api.url}>
            <input
              type="text"
              value={inputValues[api.columnName!] || ""} // columnName으로 해당 값을 가져옴
              onChange={(e) => handleInputChange(api.columnName!, e.target.value)}
              placeholder={`Enter value for ${api.columnName}`}
            />
            <button
              onClick={() =>
                onFetchData(api.url, api.method, api.columnName, inputValues[api.columnName!])
              } // idx로 해당 값 전달
              disabled={loading}
            >
              {api.method} {api.url}
            </button>
          </div>
        ) : (
          <button key={api.url} onClick={() => onFetchData(api.url, api.method)}>
            {api.method} {api.url}
          </button>
        )
      )}
    </div>
  );
};

export default ApiButtons;
