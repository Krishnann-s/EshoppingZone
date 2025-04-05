function About() {
  return (
    <div className="max-w-7xl mx-auto px-4 py-12">
      {/* Hero Section */}
      <div className="text-slate-800 text-5xl font-bold text-center mb-16">
        About Us
      </div>

      {/* Main Content Section */}
      <div className="flex flex-col lg:flex-row justify-between items-start gap-12 mb-20">
        {/* Text Content */}
        <div className="w-full lg:w-1/2 space-y-6">
          <h2 className="font-bold text-4xl text-amber-600 mb-4">
            Welcome to EShoppingZone!
          </h2>

          <p className="text-lg text-gray-700 leading-relaxed">
            At EShoppingZone, we believe in providing our customers with the
            best shopping experience possible. Founded in 2025, our mission is
            to offer high-quality products at competitive prices, all while
            delivering exceptional customer service.
          </p>

          <div className="space-y-4 pt-4">
            <div>
              <h3 className="font-bold text-2xl text-slate-800 mb-2">
                Our Story
              </h3>
              <p className="text-gray-700 leading-relaxed">
                Our journey began with a simple idea: to make shopping easy and
                enjoyable for everyone. Over the years, we've grown from a small
                startup to a trusted name in the ecommerce industry, thanks to
                our loyal customers and dedicated team.
              </p>
            </div>

            <div>
              <h3 className="font-bold text-2xl text-slate-800 mb-2">
                What We Offer
              </h3>
              <p className="text-gray-700 leading-relaxed">
                We specialize in a wide range of products, including office
                essentials, Kitchen Appliances, and Gaming Devices. Each item is
                carefully selected to ensure it meets our high standards of
                quality and value.
              </p>
            </div>

            <div>
              <h3 className="font-bold text-2xl text-slate-800 mb-2">
                Our Commitment
              </h3>
              <p className="text-gray-700 leading-relaxed">
                Customer satisfaction is our top priority. We are committed to
                providing a seamless shopping experience, from browsing our
                website to receiving your order. Our friendly customer support
                team is always here to help with any questions or concerns you
                may have.
              </p>
            </div>

            <div>
              <h3 className="font-bold text-2xl text-slate-800 mb-2">
                Join Our Community
              </h3>
              <p className="text-gray-700 leading-relaxed">
                We invite you to join our community of happy customers. Follow
                us on social media for the latest updates, promotions, and more.
                Thank you for choosing EShoppingZone – we look forward to
                serving you!
              </p>
            </div>
          </div>
        </div>

        {/* Image */}
        <div className="w-full lg:w-1/2 flex justify-center">
          <div className="rounded-xl shadow-2xl overflow-hidden transform transition-all duration-300 hover:scale-105 hover:shadow-amber-100">
            <img
              src="/home-page1.jpg"
              alt="EShoppingZone storefront"
              className="rounded-xl w-full object-cover"
            />
          </div>
        </div>
      </div>
    </div>
  );
}

export default About;
