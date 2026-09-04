#built using mc-build (https://github.com/mc-build/mc-build)

scoreboard players operation Matcher id = @s id
execute as @e[tag=walker,tag=!root] if score @s id = Matcher id at @s run function nyctocosm:mobs/death_walker/__generated__/block/34
function animated_java:ajdeathwalker/remove/this