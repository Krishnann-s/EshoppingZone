const btnStyles =
  "border-none border-amber-800 hover:bg-amber-100 px-4 rounded-3xl";
const SetQuantity = ({
  quantity,
  cardCounter,
  handeQtyIncrease,
  handleQtyDecrease,
}) => {
  return (
    <div className="flex gap-8 items-center">
      {cardCounter ? null : <div className="font-semibold">QUANTITY</div>}
      <div className="flex md:flex-row flex-col gap-4 items-center lg:text-[22px] text-sm">
        <button
          disabled={quantity <= 1}
          className={btnStyles}
          onClick={handleQtyDecrease}
        >
          -
        </button>
        <div className="text-red-500">{quantity}</div>
        <button className={btnStyles} onClick={handeQtyIncrease}>
          +
        </button>
      </div>
    </div>
  );
};

export default SetQuantity;
