package kr.flab.fream.domain.auction.model;

/**
 * 입찰 상태를 표현한다.
 * <br>
 * 입찰 상태에는 {@link Active}, {@link Inactive}, {@link Canceled}, {@link Finished} 네 가지가 있다.
 * <br>
 * 각 입찰 상태의 관계는 다음과 같다. * <br>
 * <pre>
 * {@code
 *
 *
 *
 * +----------+           +--------+           +==========+
 * | Inactive | ──────►  | active | ────────►| finished |
 * +----------+           +--------+           +==========+
 *     │                      │
 *     │                      │                +==========+
 *     └──────────────────────┴──────────────►| Canceled |
 *                                             +==========+
 * }
 * </pre>
 * <p>
 * 'Active' 상태의 Auction 은 체결가능한 입찰이다. {@code dueDate}가 지나면 'Inactive' 상태로 바뀐다. 입찰을 취소하면 'Canceled',
 * 체결되면 'Finished' 상태가 된다.
 * </p>
 * <p>
 * 'Inactive' 상태의 Auction 은 체결할 수 없는 입찰이다. 입찰 만료일을 업데이트해주면 다시 'Active' 상태로 변경할 수 있다. 이 상태에서 입찰을 취소하면
 * 'Canceled' 상태가 된다.
 * </p>
 * <p>
 * 'Canceled', 'Finished' 두 상태는 더 이상 다른 상태로 변할 수 없다. 각각 취소(삭제)된 입찰, 거래 완료된 입찰을 나타낸다. 'Inactive' 상태가
 * 'Finished' 상태가 되려면 반드시 'Active' 상태를 거쳐야 한다.
 * </p>
 */
interface AuctionState {

    void cancel(Auction auction);

    void update(Auction auction);

    void finish(Auction auction);

}
