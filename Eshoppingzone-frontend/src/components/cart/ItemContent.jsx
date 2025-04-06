import { useState } from "react";
import { HiOutlineTrash } from "react-icons/hi";
import SetQuantity from "./SetQuantity";
import { useDispatch } from "react-redux";

import { formatPrice } from "../../utils/formatPrice";
import truncateText from "../../utils/truncateText";
import {
  decreaseCartQuantity,
  increaseCartQuantity,
  removeFromCart,
} from "../../store/action";
import { toast } from "react-toastify";

const ItemContent = ({
  productId,
  productName,
  imageUrl,
  description,
  quantity,
  price,
  specialPrice,
}) => {
  const [currentQuantity, setCurrentQuantity] = useState(quantity);
  const dispatch = useDispatch();

  const handleQtyIncrease = (cartItems) => {
    dispatch(
      increaseCartQuantity(
        cartItems,
        toast,
        currentQuantity,
        setCurrentQuantity
      )
    );
  };

  const handleQtyDecrease = (cartItems) => {
    if (currentQuantity > 1) {
      const newQuantity = currentQuantity - 1;
      setCurrentQuantity(newQuantity);
      dispatch(decreaseCartQuantity(cartItems, newQuantity));
    }
  };

  const removeItemFromCart = (cartItems) => {
    dispatch(removeFromCart(cartItems, toast));
  };

  return (
    <div className="grid md:grid-cols-5 grid-cols-4 md:text-md text-sm gap-4   items-center  border-t-2 border-amber-200  rounded-md  lg:px-4  py-4 p-2">
      <div className="md:col-span-2 justify-self-start flex  flex-col gap-2 ">
        <div className="flex md:flex-row flex-col lg:gap-4 sm:gap-3 gap-0 items-start ">
          <h3 className="lg:text-[17px] text-sm font-semibold text-slate-600">
            {truncateText(productName)}
          </h3>
        </div>

        <div className="md:w-36 sm:w-24 w-12">
          <img
            src={`${
              import.meta.env.VITE_BACK_END_URL
            }/image-service/api/${imageUrl.split("/").pop()}`}
            alt={productName}
            className="md:h-36 sm:h-24 h-12 w-full object-cover rounded-md"
          />

          <div className="flex items-start gap-5 mt-3">
            <button
              onClick={() =>
                removeItemFromCart({
                  imageUrl,
                  productName,
                  description,
                  specialPrice,
                  price,
                  productId,
                  quantity,
                })
              }
              className="flex items-center font-semibold space-x-4 px-4 py-1 text-md border border-rose-600 text-rose-600 rounded-2xl hover:bg-red-100 transition-colors duration-200"
            >
              <HiOutlineTrash size={20} className="text-rose-600" />
              Remove
            </button>
          </div>
        </div>
      </div>

      <div className="justify-self-center lg:text-[17px] text-sm text-slate-600 font-semibold">
        {formatPrice(Number(specialPrice))}
      </div>

      <div className="justify-self-center">
        <SetQuantity
          quantity={currentQuantity}
          cardCounter={true}
          handeQtyIncrease={() =>
            handleQtyIncrease({
              imageUrl,
              productName,
              description,
              specialPrice,
              price,
              productId,
              quantity,
            })
          }
          handleQtyDecrease={() => {
            handleQtyDecrease({
              imageUrl,
              productName,
              description,
              specialPrice,
              price,
              productId,
              quantity,
            });
          }}
        />
      </div>

      <div className="justify-self-center lg:text-[17px] text-sm text-slate-600 font-semibold">
        {formatPrice(Number(currentQuantity) * Number(specialPrice))}
      </div>
    </div>
  );
};

export default ItemContent;
