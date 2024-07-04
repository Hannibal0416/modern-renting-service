import { MockMethod } from 'vite-plugin-mock';

export default [
  {
    url: '/oauth2/token',
    method: 'post',
    response: () => {
      return {
        "access_token": "aaaaaaaa",
        "refresh_token": "bbbbbbbb",
        "token_type": "Bearer",
        "expires_in": 17999,
      }
    },
  },
  {
    url: '/oauth2/revoke',
    method: 'post',
    response: () => {
      return ''
    },
  },
  {
    url: '/users',
    method: 'post',
    response: () => {
      return  {
        "username": "sony.lin",
        "email": "sony.lin@cdk.com",
        "phone": "0988888888"
      };
    },
  },
  {
    url: '/users',
    method: 'put',
    response: (a, b) => {
      return  a.body;
    },
  },
  {
    url: '/users',
    method: 'get',
    response: () => {
      return {
        "userName": "sony.lin",
        'firstName': 'lin',
        'lastName': 'sony',
        "email": "sony.lin@cdk.com",
        "phone": "0988888888"
      };
    },
  },
  {
    url: '/type-brand',
    method: 'get',
    response: () => {
      return {
        brand: [
          {
            id: 0,
            imageUri: "volvo",
            name: "volvo",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          },
          {
            id: 1,
            imageUri: "tesla",
            name: "tesla",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          },
          {
            id: 2,
            imageUri: "benz",
            name: "benz",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          },
        ],
        type: [
          {
            id: 0,
            imageUri: "compact",
            name: "compact",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          },
          {
            id: 1,
            imageUri: "sport",
            name: "sport",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          },
          {
            id: 2,
            imageUri: "welcab",
            name: "welcab",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          },
          {
            id: 3,
            imageUri: "van",
            name: "van",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          },
          {
            id: 4,
            imageUri: "rv",
            name: "rv",
            active: true,
            createdAt: "2024-05-22T10:16:46.710Z"
          }
        ]
      }
    },
  },
  {
    url: '/model',
    method: 'get',
    response: () => {
      return [{
        id: 'test1',
        imageUri: "https://digitalassets.tesla.com/tesla-contents/image/upload/f_auto,q_auto/Compare-Model-Y-RWD.png",
        name: "model 1",
        active: true,
        createdAt: "2024-05-22T11:06:22.124Z"
      }, {
        id: 'test2',
        imageUri: "https://digitalassets.tesla.com/tesla-contents/image/upload/f_auto,q_auto/Compare-Model-3-RWD-v2.png",
        name: "model 2",
        active: true,
        createdAt: "2024-05-22T11:06:22.124Z"
      }, {
        id: 'test3',
        imageUri: "https://digitalassets.tesla.com/tesla-contents/image/upload/h_923,w_1640,c_fit,f_auto,q_auto:best/compare-model-s",
        name: "model 3",
        active: true,
        createdAt: "2024-05-22T11:06:22.124Z"
      }, {
        id: 'test4',
        imageUri: "https://digitalassets.tesla.com/tesla-contents/image/upload/f_auto,q_auto/compare-model-x-plaid-6.8.png",
        name: "model 4",
        active: true,
        createdAt: "2024-05-22T11:06:22.124Z"
      }];
    },
  },
  {
    url: '/vehicle/:id',
    method: 'get',
    response: ({ query }) => {
      return {
        id: 'test1',
        imageUri: "https://digitalassets.tesla.com/tesla-contents/image/upload/f_auto,q_auto/Compare-Model-Y-RWD.png",
        name: `Tesla Model S ${query.id}`,
        color: 'blue',
        seats: 4,
        fuelType: "electric",
        productionYear: 2024,
        rentPrice: '$1000',
        createdAt: "2024-05-22T11:06:22.124Z"
      };
    },
  },
] as MockMethod[];
