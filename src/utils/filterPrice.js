export const filterPrice = (price) => {
    if (typeof price === "string" && price.includes(".")) {
      const [integerPart, decimalPart] = price.split(".");
      return decimalPart === "00" ? integerPart : price;
    }
    return price;
  };
  