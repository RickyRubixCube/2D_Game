package com.group.game.dashdash;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import static com.almasb.fxgl.dsl.FXGL.*;

public class PlayerComponent extends Component {

    private Vec2 velocity = new Vec2(300, 0);
    private final double GRAVITY_FORCE = 800;

    // ADD THESE TWO LINES HERE:
    private double gravityMultiplier = 1.0;
    private boolean onSurface = false;

    @Override
    public void onUpdate(double tpf) {
        // Gravity logic
        if (!onSurface) {
            velocity.y += (GRAVITY_FORCE * gravityMultiplier) * tpf;
        } else {
            velocity.y = 0;
        }

        entity.translate(velocity.x * tpf, velocity.y * tpf);
        onSurface = false;
    }

    public void jump() {
        // Adding (float) before the calculation tells Java to convert it
        velocity.y = (float) (-450 * gravityMultiplier);
        onSurface = false;
        play("jump.wav");
    }

    // This is the "Bridge" function that the Physics engine calls
    public void setOnSurface(boolean isCeiling) {
        this.onSurface = true;
        this.gravityMultiplier = isCeiling ? -1.0 : 1.0;

        // Flip the bird upside down if on ceiling
        entity.setScaleY(this.gravityMultiplier);
    }
}