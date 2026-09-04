#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s e.timer = temp0 rng
scoreboard players operation @s e.timer /= #100000 rng
scoreboard players operation @s e.timer %= #8 rng
scoreboard players add @s e.timer 4
scoreboard players operation temp0 rng = state rng
scoreboard players operation temp0 rng *= a rng
scoreboard players operation temp0 rng += c rng
scoreboard players operation temp0 rng %= m rng
scoreboard players operation state rng = temp0 rng
scoreboard players operation @s e.delay = temp0 rng
scoreboard players operation @s e.delay /= #100000 rng
scoreboard players operation @s e.delay %= #10 rng
scoreboard players add @s e.delay 0
execute facing entity @e[tag=e.swirlsummon,sort=nearest,limit=1] feet run tp @s ~ ~ ~ ~ ~
function effects:id/assign
summon marker ~ ~ ~ {Tags:["justsummoned","swirl","swirlemitter"]}
execute as @e[tag=swirlemitter,tag=justsummoned] at @s run function effects:soul_swirl/__generated__/block/631
tag @s remove justsummoned