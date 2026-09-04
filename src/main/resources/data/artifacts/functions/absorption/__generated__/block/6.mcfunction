#built using mc-build (https://github.com/mc-build/mc-build)

particle minecraft:portal ~ ~ ~ 0 0 0 3 2
execute as @a[tag=a.Owner] run function magic:calcstatsspell
execute as @e[type=#nyctocosm:livingthings,tag=!a.Owner,distance=..6,tag=!a.absorption.weakened] run function artifacts:absorption/__generated__/block/7