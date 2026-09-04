#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s e.radius = temp0 rng
scoreboard players operation @s e.radius /= #100000 rng
scoreboard players operation @s e.radius %= #12 rng
scoreboard players add @s e.radius 3
execute if predicate effects:soul_swirl/straight_chance run scoreboard players set @s e.radius 0
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s e.angveloc = temp0 rng
scoreboard players operation @s e.angveloc /= #100000 rng
scoreboard players operation @s e.angveloc %= #170 rng
scoreboard players add @s e.angveloc 100
scoreboard players operation @s e.id = @e[tag=justsummoned,tag=swirl,tag=origin] e.id
execute facing entity @e[tag=e.swirlsummon,sort=nearest,limit=1] feet run tp @s ~ ~ ~ ~90 0
tag @s remove justsummoned