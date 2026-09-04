#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation SerpentSFX timer = temp0 rng
scoreboard players operation SerpentSFX timer /= #100000 rng
scoreboard players operation SerpentSFX timer %= #60 rng
scoreboard players add SerpentSFX timer 40
execute at @e[tag=serpent,tag=segment,limit=1,sort=random] run playsound nyctocosm:serpent.woosh master @a[distance=..200] ~ ~ ~ 30