#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add alerted
playsound nyctocosm:nightmare_apparition.attack master @a[distance=..50] ~ ~ ~ 5
item replace entity @s armor.head with diamond{CustomModelData:2}
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s timer = temp0 rng
scoreboard players operation @s timer /= #100000 rng
scoreboard players operation @s timer %= #50 rng
scoreboard players add @s timer 200