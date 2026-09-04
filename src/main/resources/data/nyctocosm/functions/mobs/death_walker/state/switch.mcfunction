#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[tag=idle.trans,tag=!idle] run function nyctocosm:mobs/death_walker/state/idle
execute as @s[tag=walking.trans,tag=!walking] run function nyctocosm:mobs/death_walker/state/walking
execute as @s[tag=following.trans,tag=!following] run function nyctocosm:mobs/death_walker/state/following
execute as @s[tag=attacking.trans,tag=!attacking] run function nyctocosm:mobs/death_walker/state/attacking