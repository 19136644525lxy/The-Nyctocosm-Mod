#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation @s e.lightningrange = Lightning e.lightningrange
function effects:lightning/pathraycast
function effects:lightning/shiftmarkerpoints
execute as @e[tag=lightningsegment,tag=!finalsegmentpoint] at @s run function effects:lightning/tracepath
kill @e[tag=lightningsegment]