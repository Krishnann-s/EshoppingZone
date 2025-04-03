import {
  Dialog,
  DialogBackdrop,
  DialogPanel,
  DialogTitle,
} from "@headlessui/react";
import { useEffect, useState } from "react";
import { Divider } from "@mui/material";
import CircularProgress from "@mui/material/CircularProgress";

export default function ProductViewModel({ open, setOpen, product }) {
  const [loading, setLoading] = useState(false);

  const { productName, price, description, imageUrl, specialPrice } = product;
  // Format the image URL properly
  const formattedImageUrl = imageUrl
    ? `${import.meta.env.VITE_BACK_END_URL}/image-service/api/${imageUrl
        .split("/")
        .pop()}`
    : "";

  useEffect(() => {
    if (open) {
      setLoading(true);
      // Simulate loading time or actual image loading
      const timer = setTimeout(() => {
        setLoading(false);
      }, 1000);

      return () => clearTimeout(timer);
    }
  }, [open]);

  const handleImageLoad = () => {
    setLoading(false);
  };

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
            <div className="flex justify-center aspect-[4/3] relative">
              {loading && (
                <div className="absolute inset-0 flex items-center justify-center bg-gray-100/80">
                  <CircularProgress color="primary" />
                </div>
              )}
              <img
                src={formattedImageUrl}
                alt={productName}
                onLoad={handleImageLoad}
                className={
                  loading
                    ? "opacity-0"
                    : "opacity-100 transition-opacity duration-300"
                }
              />
            </div>

            <div className="px-6 pt-10 pb-2">
              <DialogTitle
                as="h1"
                className="lg:text-3xl sm:text-2xl text-xl font-semibold leading-6 text-banner-color1 mb-4"
              >
                {productName}
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
                className="px-4 py-2 text-sm font-semibold text-banner-color4 cursor-pointer bg-amber-500 rounded-4xl"
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
