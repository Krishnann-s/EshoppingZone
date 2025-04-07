import {
  Description,
  Dialog,
  DialogBackdrop,
  DialogPanel,
  DialogTitle,
} from "@headlessui/react";
import { FaTimes } from "react-icons/fa";

function AddressInfoModal({ open, setOpen, children }) {
  return (
    <Dialog
      open={open}
      onClose={() => setOpen(false)}
      className="relative z-50"
    >
      {/* The backdrop, rendered as a fixed sibling to the panel container */}
      <DialogBackdrop className="fixed inset-0 bg-black/30" />

      {/* Full-screen container to center the panel */}
      <div className="fixed inset-0 flex w-screen items-center justify-center p-4">
        {/* The actual dialog panel  */}
        <DialogPanel className="w-full max-w-xl transform overflow-hidden rounded-xl shadow-xl transition-all bg-white p-10">
          <div className="px-4 py-4">{children}</div>
          <div className="flex justify-end gap-4 absolute right-8 top-10">
            <button
              onClick={() => setOpen(false)}
              type="button"
              className="hover:cursor-pointer rounded-full p-2"
            >
              <FaTimes
                className="text-slate-700 hover:text-red-700"
                size={25}
              />
            </button>
          </div>
        </DialogPanel>
      </div>
    </Dialog>
  );
}

export default AddressInfoModal;
