import { useOrders } from "../hooks/useOrders";

export default function OrdersPage() {
  const { orders, isLoading } = useOrders();
  console.log(orders);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!orders?.length) {
    return <div>No orders found</div>;
  }

  return (
    <div className="space-y-8">
      <h1 className="text-2xl font-bold">Your Orders</h1>

      <div className="space-y-4">
        {orders.map((order) => (
          <div
            key={order.id}
            className="bg-white p-6 rounded-lg shadow-sm space-y-4"
          >
            <div className="flex justify-between items-center">
              <div>
                <p className="text-sm text-gray-600">Order #{order.id}</p>
                <p className="text-sm text-gray-600">
                  {new Date(order.order_date).toLocaleDateString()}
                </p>
              </div>
              <div className="flex items-center gap-2">
                <span className="text-sm text-gray-600">Status:</span>
                <span className="px-2 py-1 text-sm rounded-full bg-blue-100 text-blue-800">
                  {order.status}
                </span>
              </div>
            </div>

            <div className="divide-y">
              {order.items.map((item) => (
                <div key={item.id} className="py-4 flex items-center gap-4">
                  <img
                    src={item.product_photo}
                    alt={item.product_name}
                    className="w-16 h-16 object-cover rounded"
                  />
                  <div className="flex-1">
                    <p className="font-medium">{item.product_name}</p>
                    <p className="text-sm text-gray-600">
                      ${item.price_at_time.toFixed(2)} x {item.quantity}
                    </p>
                  </div>
                  <p className="font-medium">${item.subtotal.toFixed(2)}</p>
                </div>
              ))}
            </div>

            <div className="flex justify-between items-center pt-4 border-t">
              <p className="font-medium">Total Amount:</p>
              <p className="text-lg font-bold">
                ${order.total_amount.toFixed(2)}
              </p>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

