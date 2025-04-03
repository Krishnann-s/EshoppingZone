import { MagnifyingGlass } from "react-loader-spinner";

const Loader = ({ text }) => {
  return (
    <div className="flex justify-center items-center h-[450px] w-full bg-amber-50">
      <div className="flex flex-col items-center gap- bg-amber-50">
        <MagnifyingGlass
          visible={true}
          height="80"
          width="80"
          ariaLabel="magnifying-glass-loading"
          wrapperStyle={{}}
          wrapperClass="magnifying-glass-wrapper"
          glassColor="#c0efff"
          color="#EA7300"
        />
        <p className="text-amber-800">{text ? text : "Please wait ..."}</p>
      </div>
    </div>
  );
};

export default Loader;
