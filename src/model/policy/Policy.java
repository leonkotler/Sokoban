package model.policy;

import utils.Direction;

public interface Policy {
    boolean checkPolicy(Direction direction);
}
