#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Matching e.id = @s e.id
execute as @e[tag=swirl,tag=swirlemitter] if score @s e.id = Matching e.id run tag @s add e.matched
tag @s add e.matched
scoreboard players remove @s[tag=!delayed] e.timer 1
execute as @s[scores={e.timer=..0}] run function effects:soul_swirl/__generated__/block/625
scoreboard players remove @s[scores={e.delay=1..}] e.delay 1
execute as @s[scores={e.delay=..0}] run function effects:soul_swirl/__generated__/block/626
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
execute as @s[tag=!delayed] at @s run function effects:soul_swirl/move
tag @e[tag=e.matched] remove e.matched