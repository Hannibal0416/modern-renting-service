import React from "react";
import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import axios from "axios";

interface CarDetails {
  id: string;
  name: string;
  imageUri: string;
  color: string;
  seats: number;
  fuelType: string;
  productionYear: number;
  rentPrice: number;
  createdAt: string;
}

const capitalizeFirstLetter = (str: string) => {
  return str.charAt(0).toUpperCase() + str.slice(1);
}

const SpecBlock = ({ value, title }: { value: string | number, title: string}) => {
  let displayValue = value
  if (typeof value === 'string') displayValue = capitalizeFirstLetter(value)
  return (
    <p className="w-48 mx-auto my-2 px-6 py-2 border border-gray-200 rounded-l flex flex-col items-start select-none">
      <h2 className="text-lg font-semibold">{displayValue}</h2>
      <p className="text-gray-700 text-xs">{title}</p>
    </p>
  )
}

const fetchDetail = async (id: string): Promise<CarDetails> => {
  const response = await axios.get<CarDetails>(`/vehicle/${id}`);
  return response.data;
};

const CarDetail: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const { data: carDetails, isLoading, isError } = useQuery({
    queryKey: ["detail", id],
    queryFn: ({ queryKey }) => {
      const [, queryId] = queryKey;
      return fetchDetail(queryId as string);
    },
  });

  if (isLoading) return <div>Loading...</div>;
  if (isError || !carDetails) return <div>Error loading details or car not found.</div>;

  return (
    <div className="container mx-auto px-4 py-8 flex">
      <div>
        <img
          src={carDetails?.imageUri}
          alt={carDetails?.name}
          className="w-full max-w-md object-cover mb-4"
        />
        <div className="text-xl text-left px-2">
          {carDetails?.name}
        </div>
        <div className="flex mt-4">
          <div className="px-2 py-1 mx-2 rounded-xl text-xs bg-lime-300">Good Deal | $200 under</div>
          <div className="px-2 py-1 mx-2 rounded-xl text-xs bg-gray-400">EV Battery Rating | Excellent</div>
        </div>
      </div>

      <div className="flex flex-col justify-between items-start">
        <h1 className="text-2xl font-bold">Key Specs</h1>
        <SpecBlock value={carDetails.color} title="Color" />
        <SpecBlock value={carDetails.seats} title="Seats" />
        <SpecBlock value={carDetails.fuelType} title="Fuel Type" />
        <SpecBlock value={carDetails.productionYear} title="Production Year" />
        <SpecBlock value={carDetails.rentPrice} title="Rent Price" />

        {/* <button style={{padding: '5px', display: 'block', width: '100%', fontWeight: '900', background: '#999', color: '#fff', marginTop: '20px'}}>ADD TO CART</button> */}
      </div>
    </div>
  );
};

export default CarDetail;
