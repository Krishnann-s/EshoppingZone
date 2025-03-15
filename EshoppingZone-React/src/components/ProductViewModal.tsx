import {
  Dialog,
  DialogBackdrop,
  DialogPanel,
  DialogTitle,
} from "@headlessui/react";
import { useState } from "react";
import { Divider } from "@mui/material";

export default function ProductViewModel({ open, setOpen, product }) {
  const [loading, setLoading] = useState(false);

  const handleClickLoading = () => {
    setLoading(true);
    // Simulate a network request
    setTimeout(() => {
      setLoading(false);
    }, 2000);
  };

  const { title, price, description, category, image, specialPrice } = product;

  console.log(product);
  return (
    <Dialog
      open={open}
      as="div"
      className="relative z-10 focus:outline-none"
      onClose={() => setOpen(false)}
    >
      <DialogBackdrop className="fixed inset-0 bg-banner-color1/30 bg-opacity-70 transition-opacity" />
      <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
        <div className="flex min-h-full items-center justify-center p-4">
          <DialogPanel
            transition
            className="relative transform overflow-hidden rounded-lg bg-amber-50 shadow-xl max-w-lg w-full transition-all md:max-w-[620px] md:min-w-[620px] w-full"
          >
            <div className="flex justify-center aspect-[4/3]">
              <img src={image} alt={title} />
            </div>

            <div className="px-6 pt-10 pb-2">
              <DialogTitle
                as="h1"
                className="lg:text-3xl sm:text-2xl text-xl font-semibold leading-6 text-banner-color1 mb-4"
              >
                {title}
              </DialogTitle>
              <div className="space-y-2 text-banner-color1/50 pb-4">
                <div className="flex items-center justify-between gap-2">
                  {specialPrice ? (
                    <div className="flex items-center gap-2">
                      <span className="text-banner-color1/50 line-through">
                        ${Number(price).toFixed(2)}
                      </span>
                      <span className="text-banner-color1 text-xl font-semibold">
                        ${Number(specialPrice).toFixed(2)}
                      </span>
                    </div>
                  ) : (
                    <span className="text-xl font-bold text-banner-color1">
                      {"  "}${Number(price).toFixed(2)}
                    </span>
                  )}
                </div>
                <Divider />
                <p className="text-banner-color1">{description}</p>
              </div>
            </div>
            <div className="px-6 py-4 flex justify-end gap-4">
              <button
                onClick={() => setOpen(false)}
                className="px-4 py-2 text-sm font-semibold text-banner-color4 cursor-pointer bg-banner-color6 rounded-4xl"
              >
                Close
              </button>
            </div>
          </DialogPanel>
        </div>
      </div>
    </Dialog>
  );
}
