#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players add @s e.timer 1
execute if score @s e.timer matches 3.. run kill @s
execute summon marker run function effects:elements/dissonance/transmutate/__generated__/block/607