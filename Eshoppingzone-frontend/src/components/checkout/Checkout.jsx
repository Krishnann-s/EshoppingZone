import { Step, StepLabel, Stepper } from "@mui/material";
import { useState } from "react";
import AddressInfo from "./AddressInfo";

function Checkout() {
  const [activeStep, setActiveStep] = useState(0);
  const steps = ["Addresses", "Payment Method", "Order Summary", "Payment"];
  return (
    <div className="py-14 min-h[calc(100vh-100px)]">
      <Stepper activeStep={activeStep} alternativeLabel>
        {steps.map((label, index) => (
          <Step key={index}>
            <StepLabel>{label}</StepLabel>
          </Step>
        ))}
      </Stepper>
      <div className="mt-5 ">{activeStep === 0 && <AddressInfo />}</div>
    </div>
  );
}

export default Checkout;
