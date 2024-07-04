import React from "react";
import { useQuery } from "@tanstack/react-query";
import { Link } from "react-router-dom";
import axios from '../util/axios';
import compactImage from "../assets/compact.jpg";
import sportCarImage from "../assets/sport-car.jpg";
import welcabImage from "../assets/welcab.jpg";
import vanImage from "../assets/van.jpg";
import volvoImage from "../assets/volvo.jpg";
import teslaImage from "../assets/tesla.jpg";
import benzImage from "../assets/benz2.jpg";
import rvImage from "../assets/rv.jpg";

type ImageMap = {
  [key: string]: string;
};

interface CarModel {
  id: number;
  imageUri: string;
  name: string;
  active: boolean;
  createAt: string;
}

interface ApiResponse {
  brand: CarModel[];
  type: CarModel[];
}

const images: ImageMap = {
  volvo: volvoImage,
  tesla: teslaImage,
  benz: benzImage,
  compact: compactImage,
  sport: sportCarImage,
  welcab: welcabImage,
  van: vanImage,
  rv: rvImage,
};

const fetchTypeBrand = async (): Promise<ApiResponse> => {
  const response = await axios.get<ApiResponse>("/type-brand");
  return response.data;
};

const Home: React.FC = () => {
  const { data, isError, error } = useQuery({
    queryKey: ["type-brand"],
    queryFn: fetchTypeBrand,
  });

  if (isError) return <div>Something Went Wrong. {error.message}</div>;

  return (
    <div className="container mx-auto px-4">
      <h2 className="text-2xl font-bold text-gray-800 my-8">
        Car types you might like
      </h2>
      <div className="flex flex-wrap justify-center gap-9">
        {data?.type.map((model, index) => (
          <Link
            key={index}
            to={`collection?typeId=${model.id}`}
            className="w-full sm:w-1/2 md:w-1/4 lg:w-1/4 max-w-sm rounded-xl overflow-hidden shadow-md hover:shadow-xl transition-shadow duration-300"
          >
            <img
              className="w-full h-48 object-cover"
              src={images[model.name]}
              alt={model.name}
            />
            <div className="px-6 py-1">
              <div className="font-bold text-xl mb-2 text-black hover:text-black">
                {model.name}
              </div>
            </div>
          </Link>
        ))}
      </div>
      <h2 className="text-2xl font-bold text-gray-800 my-8">
        Car brands you might like
      </h2>
      <div className="flex flex-wrap justify-center gap-9">
        {data?.brand.map((model, index) => (
          <Link
            key={index}
            to={`collection?brandId=${model.id}`}
            className="w-full sm:w-1/2 md:w-1/4 lg:w-1/4 max-w-sm rounded-xl overflow-hidden shadow-md hover:shadow-xl transition-shadow duration-300"
          >
            <img
              className="w-full h-48 object-cover"
              src={images[model.name]}
              alt={model.name}
            />
            <div className="px-6 py-1">
              <div className="font-bold text-xl mb-2 text-black hover:text-black">
                {model.name}
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default Home;
