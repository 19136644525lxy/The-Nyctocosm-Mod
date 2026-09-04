#built using mc-build (https://github.com/mc-build/mc-build)

execute as @s[type=!player] rotated ~ ~ positioned as @s run function spells:__generated__/block/136
execute as @s[type=player] unless entity @s[nbt={RootVehicle:{}}] rotated ~ ~ positioned as @s if block ^ ^ ^2 #nyctocosm:passthrough run function spells:__generated__/block/137