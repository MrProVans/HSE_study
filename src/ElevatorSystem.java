import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

class Elevator {
    private final int id;
    private int currentFloor = 0;
    private boolean moving = false;

    public Elevator(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public boolean isMoving() {
        return moving;
    }

    public void moveTo(int targetFloor) {
        if (currentFloor == targetFloor) {
            System.out.println("[Лифт " + id + "] ⚠️ Уже на этаже " + targetFloor + ", запрос игнорируется");
            return;
        }

        System.out.println("[Лифт " + id + "] ⏳ Движется к этажу " + targetFloor);
        moving = true;

        try {
            Thread.sleep(1000); // Имитация движения лифта
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        currentFloor = targetFloor;
        moving = false;
        System.out.println("[Лифт " + id + "] ✅ Прибыл на этаж " + targetFloor);
    }
}

class Dispatcher {
    private final Map<Integer, Queue<Integer>> requests = new HashMap<>();

    public Dispatcher(List<Elevator> elevators) {
        // Инициализация очередей запросов для каждого лифта
        for (Elevator elevator : elevators) {
            requests.put(elevator.getId(), new ConcurrentLinkedQueue<>());
        }
    }

    public void addRequest(int floor, Elevator elevator) {
        Queue<Integer> elevatorQueue = requests.get(elevator.getId());

        if (!elevatorQueue.contains(floor)) {
            elevatorQueue.offer(floor);
            System.out.println("[Диспетчер] 📡 Запрос на этаж " + floor + " направлен лифту " + elevator.getId());
        } else {
            System.out.println("[Диспетчер] ⚠️ Запрос на этаж " + floor + " уже в очереди лифта " + elevator.getId());
        }
    }

    public Integer getNextRequest(Elevator elevator) {
        Queue<Integer> elevatorQueue = requests.get(elevator.getId());
        return elevatorQueue.poll();
    }
}

class RequestGenerator {
    private final Random random = new Random();
    private final Set<Integer> activeRequests = new HashSet<>();
    private final int maxFloor;

    public RequestGenerator(int maxFloor) {
        this.maxFloor = maxFloor;
    }

    public void generateRequest(Dispatcher dispatcher, List<Elevator> elevators) {
        int floor = random.nextInt(maxFloor + 1);

        if (!activeRequests.contains(floor)) {
            activeRequests.add(floor);
            Elevator elevator = selectOptimalElevator(elevators, floor);
            System.out.println("[Генератор запросов] 📞 Сгенерирован запрос на этаж " + floor);
            dispatcher.addRequest(floor, elevator);
        } else {
            System.out.println("[Генератор запросов] ⚠️ Запрос на этаж " + floor + " уже существует");
        }
    }

    private Elevator selectOptimalElevator(List<Elevator> elevators, int targetFloor) {
        return elevators.stream()
                .min(Comparator.comparingInt(elevator -> Math.abs(elevator.getCurrentFloor() - targetFloor)))
                .orElseThrow(() -> new IllegalStateException("Нет доступных лифтов"));
    }
}

public class ElevatorSystem {
    public static void main(String[] args) {
        List<Elevator> elevators = List.of(new Elevator(0), new Elevator(1));
        Dispatcher dispatcher = new Dispatcher(elevators);
        RequestGenerator generator = new RequestGenerator(10); // Макс. этажи: 0-10

        // Основной цикл симуляции
        new Thread(() -> {
            for (int i = 0; i < 20; i++) { // Генерация 20 запросов
                generator.generateRequest(dispatcher, elevators);
                try {
                    Thread.sleep(2000); // Интервал генерации запросов
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        while (true) {
            for (Elevator elevator : elevators) {
                Integer nextFloor = dispatcher.getNextRequest(elevator);
                if (nextFloor != null) {
                    elevator.moveTo(nextFloor);
                }
            }
            try {
                Thread.sleep(1000); // Интервал обработки очереди
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}