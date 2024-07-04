import React from "react";
import { useQuery } from "@tanstack/react-query";
import axios from "@/util/axios";
import { Link, useParams, useSearchParams } from "react-router-dom";

interface CarModel {
  id: string;
  imageUri: string;
  name: string;
  active: boolean;
  createdAt: string;
}

type ApiResponse = CarModel[];

const fetchModel = async (typeId: string|null): Promise<ApiResponse> => {
  const response = await axios.get<ApiResponse>(`/vehicles/api/model?typeId=${typeId}`);
  return response.data;
};

const Collection: React.FC = () => {
  const [searchParams] = useSearchParams();
  const typeId = searchParams.get("typeId");
  const { data: carModels } = useQuery({
    queryKey: ["model", typeId],
    queryFn: ({queryKey}) => fetchModel(queryKey[1]),
  });

  return (
    <div className="container mx-auto px-4 py-8">
      <h2 className="text-2xl font-bold text-center mb-6">Car Models</h2>
      <div className="flex flex-wrap -mx-2">
        {carModels?.map((model) => (
          <Link to={`/detail/${model.id}`} key={model.id} className="w-1/2 p-2">
            <div className="bg-white rounded-lg overflow-hidden shadow-md hover:shadow-xl transition-shadow duration-300">
              <img
                src={model.imageUri}
                alt={model.name}
                className="w-full h-48 object-cover"
              />
              <div className="p-4">
                <h3 className="font-semibold text-xl text-black hover:text-black">
                  {model.name}
                </h3>
              </div>
            </div>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default Collection;
