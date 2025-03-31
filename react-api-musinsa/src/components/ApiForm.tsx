import React, { useState } from "react";

type ApiFormProps = {
  onSubmit: (formValues: { [key: string]: string }) => Promise<void>;
};

const productData = ["id", "brand", "category", "price"];

const ApiForm: React.FC<ApiFormProps> = ({ onSubmit }) => {
  const [formValues, setFormValues] = useState<{ [key: string]: string }>({
    id: "",
    brand: "",
    category: "",
    price: "",
  });

  const handleChange = (columnName: string, value: string) => {
    setFormValues((prevValues) => ({
      ...prevValues,
      [columnName]: value, // columnName을 키로 사용하여 각 값 업데이트
    }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    await onSubmit(formValues); // API 호출
  };

  return (
    <div>
      <h4>상품 업데이트</h4>
      <form onSubmit={handleSubmit}>
        {productData.map((column) => (
          <input
            key={column}
            required={column === "id"}
            type="text"
            value={formValues[column] || ""} // undefined 방지
            onChange={(e) => handleChange(column, e.target.value)}
            placeholder={`Enter ${column}`}
          />
        ))}
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default ApiForm;
