#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove justsummoned
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s s.beamDelay = temp0 rng
scoreboard players operation @s s.beamDelay /= #100000 rng
scoreboard players operation @s s.beamDelay %= #140 rng
scoreboard players add @s s.beamDelay 140