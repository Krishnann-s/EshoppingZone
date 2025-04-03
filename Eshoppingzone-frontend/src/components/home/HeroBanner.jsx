import { Swiper, SwiperSlide } from "swiper/react";
import {
  EffectFade,
  Navigation,
  Autoplay,
  Pagination as SwiperPagination,
} from "swiper/modules";
import { bannerLists } from "../../utils";

// Import Swiper styles
import "swiper/css";
import "swiper/css/navigation";
import "swiper/css/pagination";
import "swiper/css/effect-fade";
import { Link } from "react-router-dom";

const HeroBanner = () => {
  return (
    <div className="pb-2">
      <div className="rounded-xl bg-amber-400 shadow-lg overflow-hidden">
        <Swiper
          grabCursor={true}
          spaceBetween={30}
          slidesPerView={1}
          autoplay={{ delay: 4000, disableOnInteraction: false }}
          navigation={true}
          modules={[SwiperPagination, EffectFade, Navigation, Autoplay]}
          pagination={{ clickable: true }}
          scrollbar={{ draggable: true }}
          className="rounded-2xl overflow-hidden"
        >
          {bannerLists.map((item, i) => (
            <SwiperSlide key={i}>
              <div className="flex flex-col md:flex-row items-center justify-between px-4 sm:h-[500px] h-96">
                {/* Left side - Text content (centered vertically) */}
                <div className="w-full md:w-1/2 flex flex-col items-center justify-center mb-4 md:mb-0 md:pr-4">
                  <div className="text-center">
                    <h3 className="text-3xl text-black font-bold">
                      {item.title}
                    </h3>
                    <h1 className="text-5xl text-black font-bold mt-2">
                      {item.subtitle}
                    </h1>
                    <p className="text-black font-bold mt-4">
                      {item.description}
                    </p>
                    <div className="flex justify-center">
                      <Link
                        className="mt-6 inline-block bg-black text-white py-2 px-4 rounded-2xl hover:bg-gray-800"
                        to="/products"
                      >
                        Shop
                      </Link>
                    </div>
                  </div>
                </div>

                {/* Right side - Image */}
                <div className="w-full md:w-1/2 flex justify-center items-center md:pl-4">
                  {item.image && (
                    <img
                      src={item.image}
                      alt={item.title}
                      className="max-h-[400px] w-auto object-contain rounded-md overflow-hidden shadow-xl"
                    />
                  )}
                </div>
              </div>
            </SwiperSlide>
          ))}
        </Swiper>
      </div>
    </div>
  );
};

export default HeroBanner;
